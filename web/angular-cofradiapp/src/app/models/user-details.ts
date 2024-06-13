export interface UserDetails {
  username: string;
  email: string;
  nombre: string;
  apellidos: string;
  avatar: string;
  hermandadId: string;
  hermandadesFavoritas: HermandadesFavorita[];
  cards: Card[];
}

export interface HermandadesFavorita {
  id: string;
  nombre: string;
  escudo?: string;
}

export interface Card {
  id: number;
  titulo: string;
  imagen: string;
  nombreHermandad: string;
}
