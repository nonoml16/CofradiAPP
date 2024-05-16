import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/screen/hermandades/hermandades.dart';
import 'package:flutter_cofradiapp/widgets/bottom_navbar_wigdet.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;
  static const TextStyle optionStyle =
      TextStyle(fontSize: 30, fontWeight: FontWeight.bold);
  static const List<Widget> _widgetOptions = <Widget>[
    Text(
      'Index 0: Home',
      style: optionStyle,
    ),
    HermandadesScreen(),
    Text(
      'Index 2: School',
      style: optionStyle,
    ),
    Text(
      'Index 2: School',
      style: optionStyle,
    ),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('BottomNavigationBar Sample'),
        ),
        body: Center(
          child: _widgetOptions.elementAt(_selectedIndex),
        ),
        bottomNavigationBar: const NavigationExample());
  }
}
/*
class _HomeScreenState extends State<HomeScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBody: true,
      floatingActionButton: FloatingActionButton(
        onPressed: () {},
        backgroundColor: const Color.fromARGB(255, 69, 25, 85),
        shape: const CircleBorder(),
        child: const Icon(
          Icons.qr_code_scanner,
          color: Color.fromARGB(255, 251, 231, 245),
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        padding: const EdgeInsets.symmetric(horizontal: 10),
        height: 60,
        color: const Color.fromARGB(255, 251, 231, 245),
        shape: const CircularNotchedRectangle(),
        notchMargin: 5,
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            IconButton(
              icon: const Icon(
                Icons.home,
                color: Color.fromARGB(255, 69, 25, 85),
              ),
              onPressed: () {},
            ),
            IconButton(
              icon: const Icon(
                Icons.search,
                color: Color.fromARGB(255, 69, 25, 85),
              ),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) =>
                          HermandadesScreen()), // Redireccionar a CardsPage
                );
              },
            ),
            IconButton(
              icon: const Icon(
                Icons.print,
                color: Color.fromARGB(255, 69, 25, 85),
              ),
              onPressed: () {},
            ),
            IconButton(
              icon: const Icon(
                Icons.people,
                color: Color.fromARGB(255, 69, 25, 85),
              ),
              onPressed: () {},
            ),
          ],
        ),
      ),
    );
  }
}
*/
