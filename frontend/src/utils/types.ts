import { MenuProps } from "antd";

export type GlobalState = {
  page: Page,
  user?: User,
}

export enum Page {
  MAIN,
  LOGIN
}

export type UserNoID = {
  username: string;
  email: string;
  name: string;
  surname: string;
}

export type User = UserNoID & {
  id: number;
}

export type Location = {
  latitude: number;
  longitude: number;
}

export type GradskiUred = {
  id: number;
  naziv: string;
  ostecenje: TipOstecenja;
}

export type TipOstecenja = {
  id: number;
  naziv: string;
}

export type BarebonesPrijava = {
  naziv: string;
  opis: string;
  ured: number;
  latitude: number;
  longitude: number;
}

export type Prijava = {
  id: number;
  naziv: string;
  opis: string;
  lokacija: Location;
  ured: GradskiUred;
  kreator?: User;
  parentPrijava?: Prijava;
  prvoVrijemePrijave: Date;
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
