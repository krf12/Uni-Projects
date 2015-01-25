var saturnTexture;
var saturnRingsTexture;

function initSaturnTexture() {
    saturnTexture = gl.createTexture();
    saturnTexture.image = new Image();
    saturnTexture.image.onload = function () {
        handleLoadedTexture(saturnTexture)
    }

    saturnTexture.image.src = "textures/saturnmap.jpg";
}
 function initSaturnRingsTexture(){
   saturnRingsTexture = gl.createTexture();
   saturnRingsTexture.image = new Image();
   saturnRingsTexture.image.onload = function () {
       handleLoadedTexture(saturnRingsTexture)
   }

   saturnRingsTexture.image.src = "textures/ringsRGBA.png";
 }

var saturnRotationMatrix = mat4.create();
mat4.identity(saturnRotationMatrix);

var saturnVertexPositionBuffer;
var saturnVertexNormalBuffer;
var saturnVertexTextureCoordBuffer;
var saturnVertexIndexBuffer;

var saturnRingsVertexPositionBuffer;
var saturnRingsVertexNormalBuffer;
var saturnRingsVertexTextureCoordBuffer;
var saturnRingsVertexIndexBuffer;

function initSaturnBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 6.0;

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

    saturnVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, saturnVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    saturnVertexNormalBuffer.itemSize = 3;
    saturnVertexNormalBuffer.numItems = normalData.length / 3;

    saturnVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, saturnVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    saturnVertexTextureCoordBuffer.itemSize = 2;
    saturnVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    saturnVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, saturnVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    saturnVertexPositionBuffer.itemSize = 3;
    saturnVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    saturnVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, saturnVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    saturnVertexIndexBuffer.itemSize = 1;
    saturnVertexIndexBuffer.numItems = indexData.length;


}

function initSaturnRingsBuffers(){
var ringVertices = [
    // Front face
    -15.5, -15.5, 0.0,
     15.5, -15.5, 0.0,
     15.5, 15.5, 0.0,
    -15.5, 15.5, 0.0,
    -15.5, 15.5, 0.0
    ];

    var ringNormals = [
    // Front face
     0.0, 0.0, 1.0,
     0.0, 0.0, 1.0,
     0.0, 0.0, 1.0,
     0.0, 0.0, 1.0
    ];

    var ringTextureCoords = [
  // Front face
  0.0, 0.0,
  1.0, 0.0,
  1.0, 1.0,
  0.0, 1.0
    ];

    var ringVertexIndices = [
    0, 1, 2, 0, 2, 3
    ];

    saturnRingsVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, saturnRingsVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(ringNormals), gl.STATIC_DRAW);
    saturnRingsVertexNormalBuffer.itemSize = 3;
    saturnRingsVertexNormalBuffer.numItems = ringNormals.length;

    saturnRingsVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, saturnRingsVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(ringTextureCoords), gl.STATIC_DRAW);
    saturnRingsVertexTextureCoordBuffer.itemSize = 2;
    saturnRingsVertexTextureCoordBuffer.numItems = ringTextureCoords.length;

    saturnRingsVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, saturnRingsVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(ringVertices), gl.STATIC_DRAW);
    saturnRingsVertexPositionBuffer.itemSize = 3;
    saturnRingsVertexPositionBuffer.numItems = ringVertices.length;

    saturnRingsVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, saturnRingsVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(ringVertexIndices), gl.STATIC_DRAW);
    saturnRingsVertexIndexBuffer.itemSize = 1;
    saturnRingsVertexIndexBuffer.numItems = ringVertexIndices.length;

    var newRotationMatrix = mat4.create();
    mat4.identity(newRotationMatrix);
    mat4.rotate(newRotationMatrix, degToRad(50), [1, 0, 0]);
    mat4.multiply(newRotationMatrix, sunRingsCopyMatrix, sunRingsCopyMatrix);

}

var sunSaturnCopyMatrix = mat4.create();
mat4.identity(sunSaturnCopyMatrix);
var saturnAngle = 0;
var saturnRadius = 0;
var sigAngle = 0;
var orEccentricity = 0.0565;
var orRadius = 120.8;
var angVelocity = 0.00968;

function initSaturnDrawing(matrix){

  mat4.identity(matrix);
  mat4.translate(matrix, [0,0,0]);
  mat4.multiply(matrix, sunSaturnCopyMatrix);

  mat4.multiply(matrix, sunSaturnCopyMatrix, saturnRotationMatrix);

  mat4.identity(matrix);

  var orRotationMatrix = mat4.create();
  mat4.identity(orRotationMatrix);
  saturnRadius = (orRadius*(1 + orEccentricity))/(1 + orEccentricity*Math.cos(saturnAngle));
  sigAngle = 0.5* orRadius * orRadius * angVelocity/(saturnRadius*saturnRadius);
  saturnAngle += sigAngle;
  mat4. translate(orRotationMatrix, [saturnRadius * Math.sin(saturnAngle), 0, saturnRadius*Math.cos(saturnAngle)]);
  mat4.multiply(orRotationMatrix, saturnRotationMatrix, saturnRotationMatrix);

  mat4.multiply(matrix, saturnRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, saturnTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, saturnVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, saturnVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, saturnVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, saturnVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, saturnVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, saturnVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, saturnVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, saturnVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}

var ringsRotationMatrix = mat4.create();
mat4.identity(ringsRotationMatrix);
var sunRingsCopyMatrix = mat4.create();
mat4.identity(sunRingsCopyMatrix);

function initSaturnRingsDrawing(matrix){
  mat4.identity(matrix);
  mat4.translate(matrix, [0,0,0]);
  mat4.multiply(matrix, sunRingsCopyMatrix);

  mat4.multiply(matrix, sunRingsCopyMatrix, ringsRotationMatrix);

  mat4.identity(matrix);

  var orRotationMatrix = mat4.create();
  mat4.identity(orRotationMatrix);
  saturnRadius = (orRadius*(1 + orEccentricity))/(1 + orEccentricity*Math.cos(saturnAngle));
  sigAngle = 0.5* orRadius * orRadius * angVelocity/(saturnRadius*saturnRadius);
  saturnAngle += sigAngle;
  mat4. translate(orRotationMatrix, [saturnRadius * Math.sin(saturnAngle), 0, saturnRadius*Math.cos(saturnAngle)]);
  mat4.multiply(orRotationMatrix, ringsRotationMatrix, ringsRotationMatrix);

  mat4.multiply(matrix, ringsRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, saturnRingsTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.blendFunc(gl.SRC_ALPHA, gl.ONE);
  gl.enable(gl.BLEND);
  gl.uniform1f(shaderProgram.alphaUniform, parseFloat(1000));

  gl.bindBuffer(gl.ARRAY_BUFFER, saturnRingsVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, saturnVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, saturnRingsVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, saturnVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, saturnRingsVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, saturnVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, saturnRingsVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, saturnRingsVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);

  gl.disable(gl.BLEND);
}
