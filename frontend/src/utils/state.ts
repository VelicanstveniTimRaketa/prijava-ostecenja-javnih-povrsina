import { createContext } from "react";
import { GlobalState } from "../utils/types";

export const STATE: GlobalState = {
  token: undefined,
  user: {
    id: 2,
    username: "korisnik1",
    email: "korisnik1@gmail.com",
    ime: "Korisnik",
    prezime: "Prezime",
    role: "USER",
  },
};

export const StateContext = createContext<{
  global: GlobalState,
  setGlobal: React.Dispatch<React.SetStateAction<GlobalState>>
}>({
  global: STATE,
  setGlobal: (STATE) => STATE,
});