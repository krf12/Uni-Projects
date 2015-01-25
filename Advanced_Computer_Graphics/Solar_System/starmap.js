var starTexture;

function initStarTexture() {
    starTexture = gl.createTexture();
    starTexture.image = new Image();
    starTexture.image.onload = function () {
        handleLoadedTexture(starTexture)
    }

    starTexture.image.src = "textures/stars.jpg";
}

var starRotationMatrix = mat4.create();
mat4.identity(starRotationMatrix);

var starVertexPositionBuffer;
var starVertexNormalBuffer;
var starVertexTextureCoordBuffer;
var starVertexIndexBuffer;

function initStarBuffers() {
    var latitudeBands = 50;
    var longitudeBands = 50;
    var radius = 600.0;

    var vertexPositionData = [];
    var normalData = [];
    var textureCoordData = [];
    for (var latNumber=0; latNumber <= latitudeBands; latNumber++) {
        var theta = latNumber * Math.PI / latitudeBands;
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);

        for (var longNumber=0; longNumber <= longitudeBands; longNumber++) {
            var phi = longNumber * 2 * Math.PI / longitudeBands;
            var sinPhi = Math.sin(phi);
            var cosPhi = Math.cos(phi);

            var x = cosPhi * sinTheta;
            var y = cosTheta;
            var z = sinPhi * sinTheta;
            var u = 1 - (longNumber / longitudeBands);
            var v = 1 - (latNumber / latitudeBands);

            normalData.push(-x);
            normalData.push(-y);
            normalData.push(-z);
            textureCoordData.push(u);
            textureCoordData.push(v);
            vertexPositionData.push(radius * x);
            vertexPositionData.push(radius * y);
            vertexPositionData.push(radius * z);
        }
    }

    var indexData = [];
    for (var latNumber=0; latNumber < latitudeBands; latNumber++) {
        for (var longNumber=0; longNumber < longitudeBands; longNumber++) {
            var first = (latNumber * (longitudeBands + 1)) + longNumber;
            var second = first + longitudeBands + 1;
            indexData.push(first);
            indexData.push(second);
            indexData.push(first + 1);

            indexData.push(second);
            indexData.push(second + 1);
            indexData.push(first + 1);
        }
    }

    starVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, starVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    starVertexNormalBuffer.itemSize = 3;
    starVertexNormalBuffer.numItems = normalData.length / 3;

    starVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, starVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    starVertexTextureCoordBuffer.itemSize = 2;
    starVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    starVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, starVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    starVertexPositionBuffer.itemSize = 3;
    starVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    starVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, starVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    starVertexIndexBuffer.itemSize = 1;
    starVertexIndexBuffer.numItems = indexData.length;
}

function initStarDrawing(matrix){

  mat4.identity(matrix);

  mat4.translate(matrix, [0, 0, 0]);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, starTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, starVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, starVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, starVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, starVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, starVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, starVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, starVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, starVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
