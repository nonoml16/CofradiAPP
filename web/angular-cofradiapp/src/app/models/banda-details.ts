export interface BandaDetails {
  id: string;
  nombre: string;
  anno: number;
  localidad: string;
  hermandades: Hermandad[];
}

export interface Hermandad {
  id: string;
  nombre: string;
  escudo: string;
}
