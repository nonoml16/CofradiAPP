export interface HermandadDetails {
  id: string;
  nombre: string;
  nombreCompleto: string;
  escudo: string;
  annoFundacion: number;
  deInteres: string;
  numNazarenos: number;
  numHermanos: number;
  tiempoPaso: number;
  banda: Banda[];
  sede: string;
  cards: Card[];
  imagenes: string[];
  pasos: Paso[];
}

export interface Banda {
  id: string;
  nombre: string;
}

export interface Card {
  id: number;
  titulo: string;
  imagen: string;
  nombreHermandad: string;
}

export interface Paso {
  id: string;
  imagen: string;
}
