import { MenuProps } from "antd";

export type GlobalState = {
  page: Page,
  user?: User,
}

export enum Page {
  MAIN,
  LOGIN
}

export type User = {
  id: number;
  username: string;
  email: string;
  name: string;
  surname: string;
}

export type Location = {
  latitude: number;
  longitude: number;
}

export type Vijece = {
  id: number;
  naziv: string;
}

export type TipOstecenja = {
  id: number;
  naziv: string;
  vijece: Vijece;
}

export type BarebonesPrijava = {
  tipOstecenja: { id: number };
  opis: string;
  lokacija: Location;
}

export type Prijava = BarebonesPrijava & {
  id: number;
  tipOstecenja: TipOstecenja;
  kreator?: User;
  prvoVrijemePrijave: Date;
  parentPrijava?: Prijava;
  vrijemeOtklona?: Date;
  slike: string[];
}

export type Response<T> = {
  success: boolean;
  error?: string;
  data?: T;
}

// this is some vile black magic fr
export type MenuPropsWithComponent = MenuProps["items"] extends ((infer T)[] | undefined) ? (T & { component: React.FC })[] : never;
