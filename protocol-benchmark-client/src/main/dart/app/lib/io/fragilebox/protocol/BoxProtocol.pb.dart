///
//  Generated code. Do not modify.
///
library io.fragilebox.protocol_BoxProtocol;

import 'package:fixnum/fixnum.dart';
import 'package:protobuf/protobuf.dart';

class BenchmarkResult extends GeneratedMessage {
  static final BuilderInfo _i = new BuilderInfo('BenchmarkResult')
    ..a(1, 'messageTime', PbFieldType.O6, Int64.ZERO)
    ..a(2, 'processedMessages', PbFieldType.O3)
    ..hasRequiredFields = false
  ;

  BenchmarkResult() : super();
  BenchmarkResult.fromBuffer(List<int> i, [ExtensionRegistry r = ExtensionRegistry.EMPTY]) : super.fromBuffer(i, r);
  BenchmarkResult.fromJson(String i, [ExtensionRegistry r = ExtensionRegistry.EMPTY]) : super.fromJson(i, r);
  BenchmarkResult clone() => new BenchmarkResult()..mergeFromMessage(this);
  BuilderInfo get info_ => _i;
  static BenchmarkResult create() => new BenchmarkResult();
  static PbList<BenchmarkResult> createRepeated() => new PbList<BenchmarkResult>();
  static BenchmarkResult getDefault() {
    if (_defaultInstance == null) _defaultInstance = new _ReadonlyBenchmarkResult();
    return _defaultInstance;
  }
  static BenchmarkResult _defaultInstance;
  static void $checkItem(BenchmarkResult v) {
    if (v is !BenchmarkResult) checkItemFailed(v, 'BenchmarkResult');
  }

  Int64 get messageTime => $_get(0, 1, null);
  void set messageTime(Int64 v) { $_setInt64(0, 1, v); }
  bool hasMessageTime() => $_has(0, 1);
  void clearMessageTime() => clearField(1);

  int get processedMessages => $_get(1, 2, 0);
  void set processedMessages(int v) { $_setUnsignedInt32(1, 2, v); }
  bool hasProcessedMessages() => $_has(1, 2);
  void clearProcessedMessages() => clearField(2);
}

class _ReadonlyBenchmarkResult extends BenchmarkResult with ReadonlyMessageMixin {}

class BenchmarkOptions extends GeneratedMessage {
  static final BuilderInfo _i = new BuilderInfo('BenchmarkOptions')
    ..a(3, 'benchmarkSize', PbFieldType.O3)
    ..a(4, 'payloadSize', PbFieldType.O3)
    ..hasRequiredFields = false
  ;

  BenchmarkOptions() : super();
  BenchmarkOptions.fromBuffer(List<int> i, [ExtensionRegistry r = ExtensionRegistry.EMPTY]) : super.fromBuffer(i, r);
  BenchmarkOptions.fromJson(String i, [ExtensionRegistry r = ExtensionRegistry.EMPTY]) : super.fromJson(i, r);
  BenchmarkOptions clone() => new BenchmarkOptions()..mergeFromMessage(this);
  BuilderInfo get info_ => _i;
  static BenchmarkOptions create() => new BenchmarkOptions();
  static PbList<BenchmarkOptions> createRepeated() => new PbList<BenchmarkOptions>();
  static BenchmarkOptions getDefault() {
    if (_defaultInstance == null) _defaultInstance = new _ReadonlyBenchmarkOptions();
    return _defaultInstance;
  }
  static BenchmarkOptions _defaultInstance;
  static void $checkItem(BenchmarkOptions v) {
    if (v is !BenchmarkOptions) checkItemFailed(v, 'BenchmarkOptions');
  }

  int get benchmarkSize => $_get(0, 3, 0);
  void set benchmarkSize(int v) { $_setUnsignedInt32(0, 3, v); }
  bool hasBenchmarkSize() => $_has(0, 3);
  void clearBenchmarkSize() => clearField(3);

  int get payloadSize => $_get(1, 4, 0);
  void set payloadSize(int v) { $_setUnsignedInt32(1, 4, v); }
  bool hasPayloadSize() => $_has(1, 4);
  void clearPayloadSize() => clearField(4);
}

class _ReadonlyBenchmarkOptions extends BenchmarkOptions with ReadonlyMessageMixin {}

class BenchmarkMessage_Type extends ProtobufEnum {
  static const BenchmarkMessage_Type RUN = const BenchmarkMessage_Type._(0, 'RUN');
  static const BenchmarkMessage_Type STOP = const BenchmarkMessage_Type._(1, 'STOP');
  static const BenchmarkMessage_Type PAYLOAD_REQUEST = const BenchmarkMessage_Type._(2, 'PAYLOAD_REQUEST');
  static const BenchmarkMessage_Type PAYLOAD_RESPONSE = const BenchmarkMessage_Type._(3, 'PAYLOAD_RESPONSE');

  static const List<BenchmarkMessage_Type> values = const <BenchmarkMessage_Type> [
    RUN,
    STOP,
    PAYLOAD_REQUEST,
    PAYLOAD_RESPONSE,
  ];

  static final Map<int, BenchmarkMessage_Type> _byValue = ProtobufEnum.initByValue(values);
  static BenchmarkMessage_Type valueOf(int value) => _byValue[value];
  static void $checkItem(BenchmarkMessage_Type v) {
    if (v is !BenchmarkMessage_Type) checkItemFailed(v, 'BenchmarkMessage_Type');
  }

  const BenchmarkMessage_Type._(int v, String n) : super(v, n);
}

class BenchmarkMessage extends GeneratedMessage {
  static final BuilderInfo _i = new BuilderInfo('BenchmarkMessage')
    ..e(1, 'type', PbFieldType.QE, BenchmarkMessage_Type.RUN, BenchmarkMessage_Type.valueOf)
    ..a(2, 'id', PbFieldType.Q3)
    ..p(3, 'payload', PbFieldType.PSF3)
    ..a(4, 'options', PbFieldType.OM, BenchmarkOptions.getDefault, BenchmarkOptions.create)
    ..a(5, 'result', PbFieldType.OM, BenchmarkResult.getDefault, BenchmarkResult.create)
  ;

  BenchmarkMessage() : super();
  BenchmarkMessage.fromBuffer(List<int> i, [ExtensionRegistry r = ExtensionRegistry.EMPTY]) : super.fromBuffer(i, r);
  BenchmarkMessage.fromJson(String i, [ExtensionRegistry r = ExtensionRegistry.EMPTY]) : super.fromJson(i, r);
  BenchmarkMessage clone() => new BenchmarkMessage()..mergeFromMessage(this);
  BuilderInfo get info_ => _i;
  static BenchmarkMessage create() => new BenchmarkMessage();
  static PbList<BenchmarkMessage> createRepeated() => new PbList<BenchmarkMessage>();
  static BenchmarkMessage getDefault() {
    if (_defaultInstance == null) _defaultInstance = new _ReadonlyBenchmarkMessage();
    return _defaultInstance;
  }
  static BenchmarkMessage _defaultInstance;
  static void $checkItem(BenchmarkMessage v) {
    if (v is !BenchmarkMessage) checkItemFailed(v, 'BenchmarkMessage');
  }

  BenchmarkMessage_Type get type => $_get(0, 1, null);
  void set type(BenchmarkMessage_Type v) { setField(1, v); }
  bool hasType() => $_has(0, 1);
  void clearType() => clearField(1);

  int get id => $_get(1, 2, 0);
  void set id(int v) { $_setUnsignedInt32(1, 2, v); }
  bool hasId() => $_has(1, 2);
  void clearId() => clearField(2);

  List<int> get payload => $_get(2, 3, null);

  BenchmarkOptions get options => $_get(3, 4, null);
  void set options(BenchmarkOptions v) { setField(4, v); }
  bool hasOptions() => $_has(3, 4);
  void clearOptions() => clearField(4);

  BenchmarkResult get result => $_get(4, 5, null);
  void set result(BenchmarkResult v) { setField(5, v); }
  bool hasResult() => $_has(4, 5);
  void clearResult() => clearField(5);
}

class _ReadonlyBenchmarkMessage extends BenchmarkMessage with ReadonlyMessageMixin {}

const BenchmarkResult$json = const {
  '1': 'BenchmarkResult',
  '2': const [
    const {'1': 'messageTime', '3': 1, '4': 1, '5': 3},
    const {'1': 'processedMessages', '3': 2, '4': 1, '5': 5},
  ],
};

const BenchmarkOptions$json = const {
  '1': 'BenchmarkOptions',
  '2': const [
    const {'1': 'benchmarkSize', '3': 3, '4': 1, '5': 5},
    const {'1': 'payloadSize', '3': 4, '4': 1, '5': 5},
  ],
};

const BenchmarkMessage$json = const {
  '1': 'BenchmarkMessage',
  '2': const [
    const {'1': 'type', '3': 1, '4': 2, '5': 14, '6': '.io.fragilebox.protocol.BenchmarkMessage.Type'},
    const {'1': 'id', '3': 2, '4': 2, '5': 5},
    const {'1': 'payload', '3': 3, '4': 3, '5': 15},
    const {'1': 'options', '3': 4, '4': 1, '5': 11, '6': '.io.fragilebox.protocol.BenchmarkOptions'},
    const {'1': 'result', '3': 5, '4': 1, '5': 11, '6': '.io.fragilebox.protocol.BenchmarkResult'},
  ],
  '4': const [BenchmarkMessage_Type$json],
};

const BenchmarkMessage_Type$json = const {
  '1': 'Type',
  '2': const [
    const {'1': 'RUN', '2': 0},
    const {'1': 'STOP', '2': 1},
    const {'1': 'PAYLOAD_REQUEST', '2': 2},
    const {'1': 'PAYLOAD_RESPONSE', '2': 3},
  ],
};

