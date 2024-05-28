import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/widgets/bottom_navbar_wigdet.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  Widget build(BuildContext context) {
    return const Scaffold(bottomNavigationBar: NavigationExample());
  }
}
