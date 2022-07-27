import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Native Code',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String? _batteryLevel = 'Waiting...';

  Future<void> _getBatteryLevel() async {
    final arguments = {'name': 'Mobile App'};

    //Step #1
    const batteryChannel = MethodChannel('course.flutter.dev/battery');

    try {
      //Step #2
      final batteryLevel =
          await batteryChannel.invokeMethod('getBatteryLevel', arguments);
      setState(() {
        _batteryLevel = batteryLevel;
      });
    } on PlatformException catch (error) {
      setState(() {
        _batteryLevel = null;
      });
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Native Code'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Center(
              child: Text('$_batteryLevel %'),
            ),
            ElevatedButton(
              onPressed: _getBatteryLevel,
              child: Text('Get Battery Level'),
            ),
          ],
        ));
  }
}
