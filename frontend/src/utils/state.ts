import { createContext } from "react";
import { GlobalState } from "../utils/types";

export const STATE: GlobalState = {
  user: {
    email: "sdf",
    id: 1,
    ime: "sdfs",
    prezime: "sfd",
    role:"ADMIN",
    token: "sdfsd",
    username: "sf",
  }
};

export const StateContext = createContext<{
  global: GlobalState,
  setGlobal: React.Dispatch<React.SetStateAction<GlobalState>>
}>({
  global: STATE,
  setGlobal: (STATE) => STATE,
});