import 'package:flutter/material.dart';
import 'package:flutter_cofradiapp/screen/hermandades/components/hermandad_card_widget.dart';

class HermandadesScreen extends StatefulWidget {
  const HermandadesScreen({super.key});

  @override
  State<HermandadesScreen> createState() => _HermandadesScreenState();
}

class _HermandadesScreenState extends State<HermandadesScreen> {
  @override
  Widget build(BuildContext context) {
    return HermandadCardWigdet();
  }
}
