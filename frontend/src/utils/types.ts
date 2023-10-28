import { MenuProps } from "antd";

export enum Page {
  MAIN,
  LOGIN
}

export type GlobalState = {
  page: Page,
  loggedIn: boolean,
}

// this is some vile black magic fr
export type MenuPropsWithComponent = MenuProps["items"] extends ((infer T)[] | undefined) ? (T & { component: React.FC })[] : never;
