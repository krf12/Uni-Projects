var sunTexture;

function initSunTexture() {
    sunTexture = gl.createTexture();
    sunTexture.image = new Image();
    sunTexture.image.onload = function () {
        handleLoadedTexture(sunTexture)
    }

    sunTexture.image.src = "textures/sunmap.jpg";
}

var sunRotationMatrix = mat4.create();
mat4.identity(sunRotationMatrix);

var sunVertexPositionBuffer;
var sunVertexNormalBuffer;
var sunVertexTextureCoordBuffer;
var sunVertexIndexBuffer;

function initSunBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 10;

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

    sunVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, sunVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    sunVertexNormalBuffer.itemSize = 3;
    sunVertexNormalBuffer.numItems = normalData.length / 3;

    sunVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, sunVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    sunVertexTextureCoordBuffer.itemSize = 2;
    sunVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    sunVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, sunVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    sunVertexPositionBuffer.itemSize = 3;
    sunVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    sunVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, sunVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    sunVertexIndexBuffer.itemSize = 1;
    sunVertexIndexBuffer.numItems = indexData.length;
}

function initSunDrawing(matrix){

  mat4.identity(matrix);

  mat4.translate(matrix, [0, 0, 0]);

  mat4.multiply(matrix, sunRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, sunTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, sunVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, sunVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, sunVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, sunVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, sunVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, sunVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, sunVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, sunVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
