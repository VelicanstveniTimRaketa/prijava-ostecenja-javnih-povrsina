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
  username: string;
  email: string;
  id: number;
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
  naziv: string;
  vijece: Vijece;
}

export type Prijava = {
  id: number;
  lokacija: Location;
  tipOstecenja: TipOstecenja;
  kreator?: User;
  slike: never[];
  prvoVrijemePrijave: Date;
  vrijemeOtklona?: Date;
}

export type ResponsePrijave = {
  success: boolean;
  error?: string;
  data: Prijava[];
}

// this is some vile black magic fr
export type MenuPropsWithComponent = MenuProps["items"] extends ((infer T)[] | undefined) ? (T & { component: React.FC })[] : never;
