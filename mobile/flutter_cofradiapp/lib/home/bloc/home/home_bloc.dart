import 'package:bloc/bloc.dart';
import 'package:flutter_cofradiapp/home/model/home_response.dart';
import 'package:flutter_cofradiapp/home/repositories/home_repository.dart';
import 'package:meta/meta.dart';

part 'home_event.dart';
part 'home_state.dart';

class HomeBloc extends Bloc<HomeEvent, HomeState> {
  final HomeRepository homeRepository;
  HomeBloc(this.homeRepository) : super(HomeInitial()) {
    on<HomePageFetch>(_onHomeFetch);
  }

  void _onHomeFetch(HomePageFetch event, Emitter<HomeState> emit) async {
    try {
      final homePageRes = await homeRepository.fetchHomePage();
      emit(HomeFetchSuccess(homePageRes));
      return;
    } on Exception catch (e) {
      emit(HomeFetchError(e.toString()));
    }
  }
}
