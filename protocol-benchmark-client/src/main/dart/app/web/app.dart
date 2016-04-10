import 'dart:core';
import 'dart:html';
import 'dart:async';
import 'dart:typed_data';
import 'package:boxclient/io/fragilebox/protocol/BoxProtocol.pb.dart';
import 'package:fixnum/fixnum.dart';

WebSocket ws;

outputMsg(String msg) {
  var output = querySelector('body');
  var text = msg;
  if (!output.text.isEmpty) {
    text = "${output.text}<br>${text}";
  }
  output.text = text;
}

void initWebSocket([int retrySeconds = 2]) {
  var reconnectScheduled = false;

  outputMsg("Connecting to websocket");
  ws = new WebSocket('ws://localhost:8080/echo')
    ..binaryType = 'arraybuffer';

  void scheduleReconnect() {
    if (!reconnectScheduled) {
      new Timer(new Duration(milliseconds: 1000 * retrySeconds), () => initWebSocket(retrySeconds * 2));
    }
    reconnectScheduled = true;
  }

  ws.onOpen.listen((e) {
    outputMsg('Connected');
  });

  ws.onClose.listen((e) {
    outputMsg('Websocket closed, retrying in $retrySeconds seconds');
    scheduleReconnect();
  });

  ws.onError.listen((e) {
    outputMsg("Error connecting to ws");
    scheduleReconnect();
  });

  ws.onMessage.listen((MessageEvent e) {
    final ByteBuffer buf = e.data;
    BenchmarkMessage msg = new BenchmarkMessage.fromBuffer(buf.asUint8List());

    switch (msg.type) {

    }

    Message request = new Message.fromBuffer(buf.asUint8List());

    if (MessageType.BENCHMARK_REQUEST == request.type) {
      BenchmarkResponse br = new BenchmarkResponse()
        ..id = request.benchmarkRequest.id;
      Message response = new Message()
        ..type = MessageType.BENCHMARK_RESPONSE
        ..benchmarkResponse = br;
      ws.sendByteBuffer(response.writeToBuffer().buffer);
    }
  });
}

void main() {
  //initWebSocket();
}
