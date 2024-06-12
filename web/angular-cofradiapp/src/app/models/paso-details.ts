export interface PasoDetails {
  id: string;
  imagen: string;
  capataz: string;
  numCostaleros: number;
  acompannamiento: Acompannamiento[];
  hermandad: string;
  imagenes: string[];
}

export interface Acompannamiento {
  id: string;
  nombre: string;
}
