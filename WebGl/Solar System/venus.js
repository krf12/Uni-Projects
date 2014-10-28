var venusTexture;

function initVenusTexture() {
    venusTexture = gl.createTexture();
    venusTexture.image = new Image();
    venusTexture.image.onload = function () {
        handleLoadedTexture(venusTexture)
    }

    venusTexture.image.src = "textures\\venusmap.jpg";
}

var venusRotationMatrix = mat4.create();
mat4.identity(venusRotationMatrix);

var venusVertexPositionBuffer;
var venusVertexNormalBuffer;
var venusVertexTextureCoordBuffer;
var venusVertexIndexBuffer;

function initVenusBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 1.0;

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

    venusVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, venusVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    venusVertexNormalBuffer.itemSize = 3;
    venusVertexNormalBuffer.numItems = normalData.length / 3;

    venusVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, venusVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    venusVertexTextureCoordBuffer.itemSize = 2;
    venusVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    venusVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, venusVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    venusVertexPositionBuffer.itemSize = 3;
    venusVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    venusVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, venusVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    venusVertexIndexBuffer.itemSize = 1;
    venusVertexIndexBuffer.numItems = indexData.length;
}

function initVenusDrawing(matrix){
  mat4.identity(matrix);

  mat4.translate(matrix, [0, 0, -30]);

  mat4.multiply(matrix, venusRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, venusTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, venusVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, venusVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, venusVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, venusVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, venusVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, venusVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, venusVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, venusVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
