var mercuryTexture;

function initMercuryTexture() {
    mercuryTexture = gl.createTexture();
    mercuryTexture.image = new Image();
    mercuryTexture.image.onload = function () {
        handleLoadedTexture(mercuryTexture)
    }

    mercuryTexture.image.src = "textures\\mercurymap.jpg";
}

var mercuryRotationMatrix = mat4.create();
mat4.identity(mercuryRotationMatrix);

var mercuryVertexPositionBuffer;
var mercuryVertexNormalBuffer;
var mercuryVertexTextureCoordBuffer;
var mercuryVertexIndexBuffer;

function initMercuryBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 0.25;

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

            normalData.push(x);
            normalData.push(y);
            normalData.push(z);
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

    mercuryVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, mercuryVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    mercuryVertexNormalBuffer.itemSize = 3;
    mercuryVertexNormalBuffer.numItems = normalData.length / 3;

    mercuryVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, mercuryVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    mercuryVertexTextureCoordBuffer.itemSize = 2;
    mercuryVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    mercuryVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, mercuryVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    mercuryVertexPositionBuffer.itemSize = 3;
    mercuryVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    mercuryVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, mercuryVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    mercuryVertexIndexBuffer.itemSize = 1;
    mercuryVertexIndexBuffer.numItems = indexData.length;
}

function initMercuryDrawing(matrix){
  mat4.identity(matrix);

  mat4.translate(matrix, [0, 0, -15]);

  mat4.multiply(matrix, mercuryRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, mercuryTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, mercuryVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, mercuryVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, mercuryVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, mercuryVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, mercuryVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, mercuryVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, mercuryVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, mercuryVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
