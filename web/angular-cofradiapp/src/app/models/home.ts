export interface Home {
  imagenes: string[];
  numHdades: number;
  numBandas: number;
  numCards: number;
  numUsers: number;
  hermandadDia: HermandadDia;
  cardsDestacadas: CardsDestacada[];
  hermandadesDestacadas: HermandadesDestacada[];
  musicasDestacadas: MusicasDestacada[];
}

export interface HermandadDia {
  id: string;
  nombre: string;
  escudo: string;
}

export interface CardsDestacada {
  id: number;
  titulo: string;
  imagen: string;
  nombreHermandad: string;
}

export interface HermandadesDestacada {
  id: string;
  nombre: string;
  escudo?: string;
}

export interface MusicasDestacada {
  id: string;
  nombre: string;
}
