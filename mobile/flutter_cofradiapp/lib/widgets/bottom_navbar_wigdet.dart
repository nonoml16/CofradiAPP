import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/screen/cards/cards.dart';
import 'package:flutter_cofradiapp/screen/hermandades/hermandades.dart';
import 'package:flutter_cofradiapp/screen/user/user_profile_screen.dart';
import 'package:flutter_cofradiapp/widgets/home_page_widget.dart';
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
        const HomePageScreen(),
        const HermandadesScreen(),
        const CardsScreen(),
        const UserProfileScreen()
      ][currentPageIndex],
    );
  }
}
