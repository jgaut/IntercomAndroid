document.addEventListener('DOMContentLoaded', function () {


    var localVideo = document.querySelector('#localVideo');
    var remoteVideo = document.querySelector('#remote-video');

    // Put variables in global scope to make them available to the browser console.
    var constraints = window.constraints = {
      audio: true,
      video: true
    };

function getIceServers() {
    // Call XirSys ICE servers
        $.ajax({
          url: 'https://service.xirsys.com/ice',
          data: {
            ident: 'jgaut',
            secret: '4df583aa-809a-11e5-b3fc-c3392fdb5bf9',
            domain: 'www.jgaut.com',
            application: 'default',
            room: 'default',
            secure: 1
          },
          success: function (data, status) {
            console.log(data);
            console.log(status);
          },
          async: true
        });


  }

  function handleSuccess(stream) {
    var videoTracks = stream.getVideoTracks();
    console.log('Got stream with constraints:', constraints);
    console.log('Using video device: ' + videoTracks[0].label);
    stream.oninactive = function() {
      console.log('Stream inactive');
    };
    window.stream = stream; // make variable available to browser console
    localVideo.srcObject = stream;
  }

  function handleError(error) {
    if (error.name === 'ConstraintNotSatisfiedError') {
      errorMsg('The resolution ' + constraints.video.width.exact + 'x' +
          constraints.video.width.exact + ' px is not supported by your device.');
    } else if (error.name === 'PermissionDeniedError') {
      errorMsg('Permissions have not been granted to use your camera and ' +
        'microphone, you need to allow the page access to your devices in ' +
        'order for the demo to work.');
    }
    errorMsg('getUserMedia error: ' + error.name, error);
  }

  function errorMsg(msg, error) {
    console.log(msg);
    if (typeof error !== 'undefined') {
      console.error(error);
    }
  }

    //function startVideo(){
        navigator.mediaDevices.getUserMedia(constraints).then(handleSuccess).catch(handleError);
    //}
}
);