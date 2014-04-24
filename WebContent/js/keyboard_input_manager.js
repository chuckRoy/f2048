function KeyboardInputManager() {
	
  this.events = {};

  if (window.navigator.msPointerEnabled) {
    //Internet Explorer 10 style
    this.eventTouchstart    = "MSPointerDown";
    this.eventTouchmove     = "MSPointerMove";
    this.eventTouchend      = "MSPointerUp";
  } else {
    this.eventTouchstart    = "touchstart";
    this.eventTouchmove     = "touchmove";
    this.eventTouchend      = "touchend";
  }

  this.listen();
  k = this;
}
var lock = false;
function stop() {
	//after success
	lock = true;
	$("#auto_btn").html("auto");
	$("#auto_btn").removeClass("stop-button");
	$("#auto_btn").addClass("normal-button");
	$("#auto_btn").attr("onclick", "auto()");
}

function auto() {
	lock = false;
	$("#auto_btn").html("stop");
	$("#auto_btn").removeClass("normal-button");
	$("#auto_btn").addClass("stop-button");
	$("#auto_btn").attr("onclick", "stop()");
	sendCell();
}

function sendCell() {
	if(game.isGameTerminated()) {
		$.gritter.add({
  			title : 'Status Exception',
  			text : 'the game is over...',
  			class_name : 'gritter-light'
  		});
		lock = true;
		stop();
		return;
	}
	var celljson = JSON.stringify(cell);
	$.ajax({
	  	type : "POST",
	  	data : celljson,
	  	url : "rest/f2048/cell",
	  	dataType : "json",
	  	contentType:'application/json',
	  	success : function(data, textStatus) {
	  		if(data == -1) {
	  			$.gritter.add({
	  	  			title : 'Server Exception',
	  	  			text : 'Can\'t sovle the game,or it is already over',
	  	  			class_name : 'gritter-light'
	  	  		});
	  			lock = true;
	  			stop();
	  			return;
	  		} else if(data == 0) {
	  			k.emit("move", 3);
	  		} else if(data == 1) {
	  			k.emit("move", 1);
	  		} else if(data == 2) {
	  			k.emit("move", 0);
	  		} else if(data == 3) {
	  			k.emit("move", 2);
	  		} else {
	  			$.gritter.add({
	  	  			title : 'Server Exception',
	  	  			text : 'Can\'t sovle the game' + data,
	  	  			class_name : 'gritter-light'
	  	  		});
	  		}
	  		if(!lock) sendCell();
	  	},
	  	error : function(XMLHttpRequest, textStatus, errorThrown) {
	  		$.gritter.add({
	  			title : 'Ajax Exception   ' + textStatus,
	  			text : "status code: " + XMLHttpRequest.readyState + "; "
	  					+ errorThrown,
	  			class_name : 'gritter-light'
	  		});
	  	}
	  });
}

function res() {
	var celljson = JSON.stringify(cell);
	$.ajax({
	  	type : "POST",
	  	data : celljson,
	  	url : "rest/f2048/cell",
	  	dataType : "json",
	  	contentType:'application/json',
	  	success : function(data, textStatus) {
	  		if(data == -1) {
	  			$.gritter.add({
	  	  			title : 'Server Exception',
	  	  			text : 'Can\'t sovle the game,or it is already over',
	  	  			class_name : 'gritter-light'
	  	  		});
	  		} else if(data == 0) {
	  			k.emit("move", 3);
	  		} else if(data == 1) {
	  			k.emit("move", 1);
	  		} else if(data == 2) {
	  			k.emit("move", 0);
	  		} else if(data == 3) {
	  			k.emit("move", 2);
	  		} else {
	  			$.gritter.add({
	  	  			title : 'Server Exception',
	  	  			text : 'Can\'t sovle the game' + data,
	  	  			class_name : 'gritter-light'
	  	  		});
	  		}
	  	},
	  	error : function(XMLHttpRequest, textStatus, errorThrown) {
	  		$.gritter.add({
	  			title : 'Ajax Exception' + textStatus,
	  			text : "status code: " + XMLHttpRequest.readyState + "; "
	  					+ errorThrown,
	  			class_name : 'gritter-light'
	  		});
	  	}
	  });
}

KeyboardInputManager.prototype.on = function (event, callback) {
  if (!this.events[event]) {
    this.events[event] = [];
  }
  this.events[event].push(callback);
};

KeyboardInputManager.prototype.emit = function (event, data) {
  var callbacks = this.events[event];
  if (callbacks) {
    callbacks.forEach(function (callback) {
      callback(data);
    });
  }
};

KeyboardInputManager.prototype.listen = function () {
  var self = this;
  //this = KeyboardInputManager;

  var map = {
    38: 0, // Up
    39: 1, // Right
    40: 2, // Down
    37: 3, // Left
    75: 0, // Vim up
    76: 1, // Vim right
    74: 2, // Vim down
    72: 3, // Vim left
    87: 0, // W
    68: 1, // D
    83: 2, // S
    65: 3  // A
  };

  // Respond to direction keys
  document.addEventListener("keydown", function (event) {
    var modifiers = event.altKey || event.ctrlKey || event.metaKey ||
                    event.shiftKey;
    var mapped    = map[event.which];
    if (!modifiers) {
      if (mapped !== undefined) {
        event.preventDefault();
        //KeyboardInputManager.emit("move", 0)
        // 0 up
        // 1 ->
        // 2 down
        // 3 <-
        self.emit("move", mapped);
      }
    }

    // R key restarts the game
    if (!modifiers && event.which === 82) {
      self.restart.call(self, event);
    }
  });

  // Respond to button presses
  this.bindButtonPress(".retry-button", this.restart);
  this.bindButtonPress(".restart-button", this.restart);
  this.bindButtonPress(".keep-playing-button", this.keepPlaying);

  // Respond to swipe events
  var touchStartClientX, touchStartClientY;
  var gameContainer = document.getElementsByClassName("game-container")[0];

  gameContainer.addEventListener(this.eventTouchstart, function (event) {
    if ((!window.navigator.msPointerEnabled && event.touches.length > 1) ||
        event.targetTouches > 1) {
      return; // Ignore if touching with more than 1 finger
    }

    if (window.navigator.msPointerEnabled) {
      touchStartClientX = event.pageX;
      touchStartClientY = event.pageY;
    } else {
      touchStartClientX = event.touches[0].clientX;
      touchStartClientY = event.touches[0].clientY;
    }

    event.preventDefault();
  });

  gameContainer.addEventListener(this.eventTouchmove, function (event) {
    event.preventDefault();
  });

  gameContainer.addEventListener(this.eventTouchend, function (event) {
    if ((!window.navigator.msPointerEnabled && event.touches.length > 0) ||
        event.targetTouches > 0) {
      return; // Ignore if still touching with one or more fingers
    }

    var touchEndClientX, touchEndClientY;

    if (window.navigator.msPointerEnabled) {
      touchEndClientX = event.pageX;
      touchEndClientY = event.pageY;
    } else {
      touchEndClientX = event.changedTouches[0].clientX;
      touchEndClientY = event.changedTouches[0].clientY;
    }

    var dx = touchEndClientX - touchStartClientX;
    var absDx = Math.abs(dx);

    var dy = touchEndClientY - touchStartClientY;
    var absDy = Math.abs(dy);

    if (Math.max(absDx, absDy) > 10) {
      // (right : left) : (down : up)
      self.emit("move", absDx > absDy ? (dx > 0 ? 1 : 3) : (dy > 0 ? 2 : 0));
    }
  });
};

KeyboardInputManager.prototype.restart = function (event) {
  event.preventDefault();
  this.emit("restart");
};

KeyboardInputManager.prototype.keepPlaying = function (event) {
  event.preventDefault();
  this.emit("keepPlaying");
};

KeyboardInputManager.prototype.bindButtonPress = function (selector, fn) {
  var button = document.querySelector(selector);
  button.addEventListener("click", fn.bind(this));
  button.addEventListener(this.eventTouchend, fn.bind(this));
};
