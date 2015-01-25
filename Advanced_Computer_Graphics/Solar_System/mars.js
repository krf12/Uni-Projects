var marsTexture;

function initMarsTexture() {
    marsTexture = gl.createTexture();
    marsTexture.image = new Image();
    marsTexture.image.onload = function () {
        handleLoadedTexture(marsTexture)
    }

    marsTexture.image.src = "textures/marsmap1k.jpg";
}

var marsRotationMatrix = mat4.create();
mat4.identity(marsRotationMatrix);

var marsVertexPositionBuffer;
var marsVertexNormalBuffer;
var marsVertexTextureCoordBuffer;
var marsVertexIndexBuffer;

function initMarsBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 0.33;

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

    marsVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, marsVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    marsVertexNormalBuffer.itemSize = 3;
    marsVertexNormalBuffer.numItems = normalData.length / 3;

    marsVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, marsVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    marsVertexTextureCoordBuffer.itemSize = 2;
    marsVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    marsVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, marsVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    marsVertexPositionBuffer.itemSize = 3;
    marsVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    marsVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, marsVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    marsVertexIndexBuffer.itemSize = 1;
    marsVertexIndexBuffer.numItems = indexData.length;
}

var sunMarsCopyMatrix = mat4.create();
mat4.identity(sunMarsCopyMatrix);
var marsAngle = 0;
var marsRadius = 0;
var sigAngle = 0;

function initMarsDrawing(matrix){
  var orEccentricity = 0.0935;
  var orRadius = 50.8;
  var angVelocity = 0.02407;

  mat4.identity(matrix);
  mat4.translate(matrix, [0,0,0]);
  mat4.multiply(matrix, sunMarsCopyMatrix);

  mat4.multiply(matrix, sunMarsCopyMatrix, marsRotationMatrix);

  mat4.identity(matrix);

  var orRotationMatrix = mat4.create();
  mat4.identity(orRotationMatrix);
  marsRadius = (orRadius*(1 + orEccentricity))/(1 + orEccentricity*Math.cos(marsAngle));
  sigAngle = 0.5* orRadius * orRadius * angVelocity/(marsRadius*marsRadius);
  marsAngle += sigAngle;
  mat4. translate(orRotationMatrix, [marsRadius * Math.sin(marsAngle), 0, marsRadius*Math.cos(marsAngle)]);
  mat4.multiply(orRotationMatrix, marsRotationMatrix, marsRotationMatrix);

  mat4.multiply(matrix, marsRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, marsTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, marsVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, marsVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, marsVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, marsVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, marsVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, marsVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, marsVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, marsVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
