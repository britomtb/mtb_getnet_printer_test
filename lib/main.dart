import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(title: 'Flutter Demo', theme: ThemeData(primarySwatch: Colors.blue), home: const Home());
  }
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) => Scaffold(
      appBar: AppBar(title: const Text("MTB - Teste de impressão getnet")),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(onPressed: onTapButton, child: const Text("Impressão teste")),
        ],
      ));

  void onTapButton() async {
    const channel = MethodChannel("com.mtb/printer");
    await channel.invokeMethod("TESTE_PRINTER");
  }
}
