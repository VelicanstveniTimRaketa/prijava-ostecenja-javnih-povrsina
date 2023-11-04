import { createContext } from "react";
import { GlobalState, Page } from "../utils/types";

export const STATE: GlobalState = {
  page: Page.MAIN,
  user: {
    id: 2,
    username: "korisnik1",
    email: "korisnik1@gmail.com",
    name: "Korisnik",
    surname: "Prezime",
  },
};

export const StateContext = createContext<{
  global: GlobalState,
  setGlobal: React.Dispatch<React.SetStateAction<GlobalState>>
}>({
  global: STATE,
  setGlobal: (STATE) => STATE,
});