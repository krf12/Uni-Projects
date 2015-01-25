var uranusTexture;

function initUranusTexture() {
    uranusTexture = gl.createTexture();
    uranusTexture.image = new Image();
    uranusTexture.image.onload = function () {
        handleLoadedTexture(uranusTexture)
    }

    uranusTexture.image.src = "textures/uranusmap.jpg";
}

var uranusRotationMatrix = mat4.create();
mat4.identity(uranusRotationMatrix);

var uranusVertexPositionBuffer;
var uranusVertexNormalBuffer;
var uranusVertexTextureCoordBuffer;
var uranusVertexIndexBuffer;

function initUranusBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 2.1;

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

    uranusVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, uranusVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    uranusVertexNormalBuffer.itemSize = 3;
    uranusVertexNormalBuffer.numItems = normalData.length / 3;

    uranusVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, uranusVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    uranusVertexTextureCoordBuffer.itemSize = 2;
    uranusVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    uranusVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, uranusVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    uranusVertexPositionBuffer.itemSize = 3;
    uranusVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    uranusVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, uranusVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    uranusVertexIndexBuffer.itemSize = 1;
    uranusVertexIndexBuffer.numItems = indexData.length;
}

var sunUranusCopyMatrix = mat4.create();
mat4.identity(sunUranusCopyMatrix);
var uranusAngle = 0;
var uranusRadius = 0;
var sigAngle = 0;

function initUranusDrawing(matrix){
  var orEccentricity = 0.0457;
  var orRadius = 150.0;
  var angVelocity = 0.00680;

  mat4.identity(matrix);
  mat4.translate(matrix, [0,0,0]);
  mat4.multiply(matrix, sunUranusCopyMatrix);

  mat4.multiply(matrix, sunUranusCopyMatrix, uranusRotationMatrix);

  mat4.identity(matrix);

  var orRotationMatrix = mat4.create();
  mat4.identity(orRotationMatrix);
  uranusRadius = (orRadius*(1 + orEccentricity))/(1 + orEccentricity*Math.cos(uranusAngle));
  sigAngle = 0.5* orRadius * orRadius * angVelocity/(uranusRadius*uranusRadius);
  uranusAngle += sigAngle;
  mat4. translate(orRotationMatrix, [uranusRadius * Math.sin(uranusAngle), 0, uranusRadius*Math.cos(uranusAngle)]);
  mat4.multiply(orRotationMatrix, uranusRotationMatrix, uranusRotationMatrix);

  mat4.multiply(matrix, uranusRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, uranusTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, uranusVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, uranusVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, uranusVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, uranusVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, uranusVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, uranusVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, uranusVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, uranusVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
