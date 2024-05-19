import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/screen/cards/cards.dart';
import 'package:flutter_cofradiapp/screen/hermandades/hermandades.dart';
import 'package:hexcolor/hexcolor.dart';

class NavigationExample extends StatefulWidget {
  const NavigationExample({super.key});

  @override
  State<NavigationExample> createState() => _NavigationExampleState();
}

class _NavigationExampleState extends State<NavigationExample> {
  int currentPageIndex = 0;

  @override
  Widget build(BuildContext context) {
    final ThemeData theme = Theme.of(context);
    return Scaffold(
      bottomNavigationBar: NavigationBar(
        onDestinationSelected: (int index) {
          setState(() {
            currentPageIndex = index;
          });
        },
        height: 71,
        backgroundColor: HexColor('FBE7F5'),
        selectedIndex: currentPageIndex,
        destinations: const <Widget>[
          NavigationDestination(
            selectedIcon:
                ImageIcon(AssetImage('assets/icono_inicio_selected.png')),
            icon: ImageIcon(AssetImage('assets/icono_inicio.png')),
            label: 'Inicio',
          ),
          NavigationDestination(
            selectedIcon:
                ImageIcon(AssetImage('assets/icono_hdades_selected.png')),
            icon: ImageIcon(AssetImage('assets/icono_hdades.png')),
            label: 'Hermandades',
          ),
          NavigationDestination(
            selectedIcon:
                ImageIcon(AssetImage('assets/icono_album_selected.png')),
            icon: ImageIcon(AssetImage('assets/icono_album.png')),
            label: 'Album',
          ),
          NavigationDestination(
            selectedIcon:
                ImageIcon(AssetImage('assets/icono_perfil_selected.png')),
            icon: ImageIcon(AssetImage('assets/icono_perfil.png')),
            label: 'Perfil',
          ),
        ],
      ),
      body: <Widget>[
        /// Home page
        Card(
          shadowColor: Colors.transparent,
          margin: const EdgeInsets.all(8.0),
          child: SizedBox.expand(
            child: Center(
              child: Text(
                'Home page',
                style: theme.textTheme.titleLarge,
              ),
            ),
          ),
        ),

        /// Notifications page
        const HermandadesScreen(),

        /// Messages page
        const CardsScreen(),
        const Center(
          child: Text('Hola'),
        )
      ][currentPageIndex],
    );
  }
}
