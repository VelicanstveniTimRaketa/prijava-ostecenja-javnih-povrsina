import { MenuProps } from "antd";

export type GlobalState = {
  user?: User,
  token?: string,
}

export type UserBase = {
  username: string;
  email: string;
  ime: string;
  prezime: string;
}

export type PasswordHash = string;

export type UserRegiser = UserBase & {
  password: PasswordHash;
}

export type UserLogin = {
  email: string;
  password: PasswordHash;
}

export type UserRole = "USER" | "ADMIN";

export type User = UserBase & {
  id: number;
  role: UserRole;
  ured?: GradskiUred;
}

export type Location = {
  latitude: number;
  longitude: number;
}

export type GradskiUred = {
  id: number;
  naziv: string;
  tipOstecenja: TipOstecenja;
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

export type PrijaveOptions = {
  kreatorId?: string;
  active?: string;
  ostecenjeId?: string;
  dateFrom?: string;
  dateTo?: string;
  lat?: string;
  lng?: string;
};

export type Response<T> = {
  success: boolean;
  errors?: string[];
  data?: T;
}

export type LoginData = {
  token: string;
  korisnik: User;
}

// this is some vile black magic fr
export type MenuPropsWithComponent = MenuProps["items"] extends ((infer T)[] | undefined) ? (T & { component: React.FC })[] : never;
