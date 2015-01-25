var jupiterTexture;

function initJupiterTexture() {
    jupiterTexture = gl.createTexture();
    jupiterTexture.image = new Image();
    jupiterTexture.image.onload = function () {
        handleLoadedTexture(jupiterTexture)
    }

    jupiterTexture.image.src = "textures/jupitermap.jpg";
}

var jupiterRotationMatrix = mat4.create();
mat4.identity(jupiterRotationMatrix);

var jupiterVertexPositionBuffer;
var jupiterVertexNormalBuffer;
var jupiterVertexTextureCoordBuffer;
var jupiterVertexIndexBuffer;

function initJupiterBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 7.0;

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

    jupiterVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, jupiterVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    jupiterVertexNormalBuffer.itemSize = 3;
    jupiterVertexNormalBuffer.numItems = normalData.length / 3;

    jupiterVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, jupiterVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    jupiterVertexTextureCoordBuffer.itemSize = 2;
    jupiterVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    jupiterVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, jupiterVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    jupiterVertexPositionBuffer.itemSize = 3;
    jupiterVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    jupiterVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, jupiterVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    jupiterVertexIndexBuffer.itemSize = 1;
    jupiterVertexIndexBuffer.numItems = indexData.length;
}

var sunJupiterCopyMatrix = mat4.create();
mat4.identity(sunJupiterCopyMatrix);
var jupiterAngle = 0;
var jupiterRadius = 0;
var sigAngle = 0;

function initJupiterDrawing(matrix){
  var orEccentricity = 0.0489;
  var orRadius = 87.8;
  var angVelocity = 0.01306;

  mat4.identity(matrix);
  mat4.translate(matrix, [0,0,0]);
  mat4.multiply(matrix, sunJupiterCopyMatrix);

  mat4.multiply(matrix, sunJupiterCopyMatrix, jupiterRotationMatrix);

  mat4.identity(matrix);

  var orRotationMatrix = mat4.create();
  mat4.identity(orRotationMatrix);
  jupiterRadius = (orRadius*(1 + orEccentricity))/(1 + orEccentricity*Math.cos(jupiterAngle));
  sigAngle = 0.5* orRadius * orRadius * angVelocity/(jupiterRadius*jupiterRadius);
  jupiterAngle += sigAngle;
  mat4. translate(orRotationMatrix, [jupiterRadius * Math.sin(jupiterAngle), 0, jupiterRadius*Math.cos(jupiterAngle)]);
  mat4.multiply(orRotationMatrix, jupiterRotationMatrix, jupiterRotationMatrix);

  mat4.multiply(matrix, jupiterRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, jupiterTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, jupiterVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, jupiterVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, jupiterVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, jupiterVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, jupiterVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, jupiterVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, jupiterVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, jupiterVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
