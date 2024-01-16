import { MenuProps } from "antd";

export type GlobalState = {
  user?: CurrentUser,
}

type UserBase = {
  username: string;
  email: string;
  ime: string;
  prezime: string;
}

export type UserRegiser = UserBase & {
  password: string;
}

export type UserLogin = {
  username: string;
  password: string;
}

export type UserRole = "USER" | "ADMIN";

export type User = UserBase & {
  id: number;
  role: UserRole;
  ured?: GradskiUred;
  ured_status: undefined | "pending" | "active";
}

export type CurrentUser = User & {
  token: string;
}

export type Location = {
  latitude: number;
  longitude: number;
}

export type GradskiUred = {
  id: number;
  naziv: string;
  tipOstecenja: TipOstecenja;
  active: "true" | "false";
}

export type GradskiUredDetailed = GradskiUred & {
  clanovi: User[];
};

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

export type Slika = {
  podatak: string;
  id: number;
}

export type Prijava = {
  id: number;
  naziv: string;
  opis: string;
  lokacija: Location;
  gradskiUred: GradskiUred;
  kreator?: User;
  parentPrijava?: Prijava;
  prvoVrijemePrijave: Date;
  vrijemeOtklona?: Date;
  slike: Slika[];
}

export type PrijaveOptions = {
  kreatorId?: string;
  active?: string;
  ostecenjeId?: string;
  dateFrom?: string;
  dateTo?: string;
  lat?: string;
  lng?: string;
  uredId?: string;
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

export type AddPrijavaResponse = {
  newReport: Prijava,
  nearbyReports: Prijava[]
};

export type NewGradskiUred = {
  nazivUreda: string;
  tipOstecenjeID?: string;
}

// this is some vile black magic fr
export type MenuPropsWithComponent = MenuProps["items"] extends ((infer T)[] | undefined) ? ({ item: T extends null ? { key: string } : T, component: React.FC, admin?: boolean })[] : never;
