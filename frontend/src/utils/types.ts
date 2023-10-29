import { MenuProps } from "antd";

export enum Page {
  MAIN,
  LOGIN
}

export type User = {
  username: string;
}

export type GlobalState = {
  page: Page,
  user?: User,
}

// this is some vile black magic fr
export type MenuPropsWithComponent = MenuProps["items"] extends ((infer T)[] | undefined) ? (T & { component: React.FC })[] : never;
