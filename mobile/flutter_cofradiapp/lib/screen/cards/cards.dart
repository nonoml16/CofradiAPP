import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_cofradiapp/card/bloc/bloc/card_bloc.dart';
import 'package:flutter_cofradiapp/card/repositories/card_repository.dart';
import 'package:flutter_cofradiapp/card/repositories/card_repository_impl.dart';
import 'package:flutter_cofradiapp/screen/cards/components/card_widget.dart';

class CardsScreen extends StatefulWidget {
  const CardsScreen({super.key});

  @override
  State<CardsScreen> createState() => _CardsScreenState();
}

class _CardsScreenState extends State<CardsScreen> {
  late CardRepository cardRepository;
  late ScrollController _scrollController;
  int pagination = 0;

  @override
  void initState() {
    super.initState();
    cardRepository = CardRepositoryImpl();
    _scrollController = ScrollController();
    _scrollController.addListener(_scrollListener);
  }

  _scrollListener() {
    if (_scrollController.position.pixels ==
        _scrollController.position.maxScrollExtent) {
      pagination++;
      BlocProvider.of<CardBloc>(context).add(CardFetchList(pagination));
    }
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final bloc = CardBloc(cardRepository);
        bloc.add(CardFetchList(pagination));
        return bloc;
      },
      child: _cardList(),
    );
  }

  _cardList() {
    return BlocBuilder<CardBloc, CardState>(
        builder: (BuildContext context, CardState state) {
      if (state is CardFetchSuccess) {
        return Scaffold(
          appBar: AppBar(
            title: const Text(
              'Album',
              style: TextStyle(
                fontFamily: 'WorkSans',
                fontWeight: FontWeight.w600,
                fontSize: 28.0,
              ),
            ),
          ),
          body: Padding(
            padding: const EdgeInsets.all(30.0),
            child: Column(
              children: [
                Expanded(
                    child: GridView.builder(
                  controller: _scrollController,
                  gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 2,
                      childAspectRatio: 160 / 220,
                      crossAxisSpacing: 8,
                      mainAxisSpacing: 13),
                  itemCount: state.cardList.length,
                  itemBuilder: (context, index) {
                    return SizedBox(
                      width: 160,
                      height: 220,
                      child: CardWidget(cardL: state.cardList[index]),
                    );
                  },
                ))
              ],
            ),
          ),
        );
      } else if (state is CardFetchError) {
        return Text(state.errorMessage);
      }
      return const Center(child: CircularProgressIndicator());
    });
  }
}
