var neptuneTexture;

function initNeptuneTexture() {
    neptuneTexture = gl.createTexture();
    neptuneTexture.image = new Image();
    neptuneTexture.image.onload = function () {
        handleLoadedTexture(neptuneTexture)
    }

    neptuneTexture.image.src = "textures/neptunemap.jpg";
}

var neptuneRotationMatrix = mat4.create();
mat4.identity(neptuneRotationMatrix);

var neptuneVertexPositionBuffer;
var neptuneVertexNormalBuffer;
var neptuneVertexTextureCoordBuffer;
var neptuneVertexIndexBuffer;

function initNeptuneBuffers() {
    var latitudeBands = 30;
    var longitudeBands = 30;
    var radius = 2.0;

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

    neptuneVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, neptuneVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normalData), gl.STATIC_DRAW);
    neptuneVertexNormalBuffer.itemSize = 3;
    neptuneVertexNormalBuffer.numItems = normalData.length / 3;

    neptuneVertexTextureCoordBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, neptuneVertexTextureCoordBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordData), gl.STATIC_DRAW);
    neptuneVertexTextureCoordBuffer.itemSize = 2;
    neptuneVertexTextureCoordBuffer.numItems = textureCoordData.length / 2;

    neptuneVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, neptuneVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertexPositionData), gl.STATIC_DRAW);
    neptuneVertexPositionBuffer.itemSize = 3;
    neptuneVertexPositionBuffer.numItems = vertexPositionData.length / 3;

    neptuneVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, neptuneVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indexData), gl.STATIC_DRAW);
    neptuneVertexIndexBuffer.itemSize = 1;
    neptuneVertexIndexBuffer.numItems = indexData.length;
}

var sunNeptuneCopyMatrix = mat4.create();
mat4.identity(sunNeptuneCopyMatrix);
var neptuneAngle = 0;
var neptuneRadius = 0;
var sigAngle = 0;

function initNeptuneDrawing(matrix){
  var orEccentricity = 0.0113;
  var orRadius = 160.0;
  var angVelocity = 0.00543;

  mat4.identity(matrix);
  mat4.translate(matrix, [0,0,0]);
  mat4.multiply(matrix, sunNeptuneCopyMatrix);

  mat4.multiply(matrix, sunNeptuneCopyMatrix, neptuneRotationMatrix);

  mat4.identity(matrix);

  var orRotationMatrix = mat4.create();
  mat4.identity(orRotationMatrix);
  neptuneRadius = (orRadius*(1 + orEccentricity))/(1 + orEccentricity*Math.cos(neptuneAngle));
  sigAngle = 0.5* orRadius * orRadius * angVelocity/(neptuneRadius*neptuneRadius);
  neptuneAngle += sigAngle;
  mat4. translate(orRotationMatrix, [neptuneRadius * Math.sin(neptuneAngle), 0, neptuneRadius*Math.cos(neptuneAngle)]);
  mat4.multiply(orRotationMatrix, neptuneRotationMatrix, neptuneRotationMatrix);

  mat4.multiply(matrix, neptuneRotationMatrix);

  gl.activeTexture(gl.TEXTURE0);
  gl.bindTexture(gl.TEXTURE_2D, neptuneTexture);
  gl.uniform1i(shaderProgram.samplerUniform, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, neptuneVertexPositionBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, neptuneVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, neptuneVertexTextureCoordBuffer);
  gl.vertexAttribPointer(shaderProgram.textureCoordAttribute, neptuneVertexTextureCoordBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ARRAY_BUFFER, neptuneVertexNormalBuffer);
  gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute, neptuneVertexNormalBuffer.itemSize, gl.FLOAT, false, 0, 0);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, neptuneVertexIndexBuffer);
  setMatrixUniforms();
  gl.drawElements(gl.TRIANGLES, neptuneVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}
