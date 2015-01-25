var earthTexture;
var earthCloudTexture;

function initEarthTexture() {
    earthTexture = gl.createTexture();
    earthTexture.image = new Image();
    earthTexture.image.onload = function () {
        handleLoadedTexture(earthTexture)
    }

    earthTexture.image.src = "textures/earthmap1k.jpg";

    earthCloudTexture = gl.createTexture();
    earthCloudTexture.image = new Image();
    earthCloudTexture.image.onload = function () {
        handleLoadedTexture(earthCloudTexture)
    }

    earthCloudTexture.image.src = "textures/earthclouds.png";
}

var earthRotationMatrix = mat4.create();
mat4.identity(earthRotationMatrix);

var earthVertexPositionBuffer;
var earthVertexNormalBuffer;
var earthVertexTextureCoordBuffer;
var earthVertexIndexBuffer;

function initEarthBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 0.78;

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

    earthVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, earthVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    earthVertexNormalBuffer.itemSize = 3;
    earthVertexNormalBuffer.numItems = normalData.length / 3;

    earthVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, earthVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    earthVertexTextureCoordBuffer.itemSize = 2;
    earthVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    earthVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, earthVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    earthVertexPositionBuffer.itemSize = 3;
    earthVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    earthVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, earthVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    earthVertexIndexBuffer.itemSize = 1;
    earthVertexIndexBuffer.numItems = indexData.length;
}

var sunEarthCopyMatrix = mat4.create();
mat4.identity(sunEarthCopyMatrix);
var earthAngle = 0;
var earthRadius = 0;
var sigAngle = 0;

function initEarthDrawing(matrix){
  var orEccentricity = 0.0167;
  var orRadius = 35.3;
  var angVelocity = 0.0729;

  mat4.identity(matrix);
  mat4.translate(matrix, [0,0,0]);
  mat4.multiply(matrix, sunEarthCopyMatrix);

  mat4.multiply(matrix, sunEarthCopyMatrix, earthRotationMatrix);

  mat4.identity(matrix);

  var orRotationMatrix = mat4.create();
  mat4.identity(orRotationMatrix);
  earthRadius = (orRadius*(1 + orEccentricity))/(1 + orEccentricity*Math.cos(earthAngle));
  sigAngle = 0.5* orRadius * orRadius * angVelocity/(earthRadius*earthRadius);
  earthAngle += sigAngle;
  mat4. translate(orRotationMatrix, [earthRadius * Math.sin(earthAngle), 0, earthRadius*Math.cos(earthAngle)]);
  mat4.multiply(orRotationMatrix, earthRotationMatrix, earthRotationMatrix);

  mat4.multiply(matrix, earthRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, earthVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, earthVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, earthVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, earthVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, earthVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, earthVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, earthVertexIndexBuffer);
  setMatrixUniforms();

  gl.bindTexture(gl.TEXTURE_2D, earthTexture);
  gl.drawElements(gl.TRIANGLES, earthVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);

  gl.blendFunc(gl.SRC_ALPHA, gl.ONE);
  gl.enable(gl.BLEND);
  gl.disable(gl.DEPTH_TEST);

  gl.bindTexture(gl.TEXTURE_2D, earthCloudTexture);
  gl.drawElements(gl.TRIANGLES, earthVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);

  gl.disable(gl.BLEND);
  gl.enable(gl.DEPTH_TEST);
}
