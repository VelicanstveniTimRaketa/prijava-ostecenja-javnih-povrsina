import { createContext } from "react";
import { GlobalState } from "../utils/types";

export const STATE: GlobalState = {

};

export const StateContext = createContext<{
  global: GlobalState,
  setGlobal: React.Dispatch<React.SetStateAction<GlobalState>>
}>({
  global: STATE,
  setGlobal: (STATE) => STATE,
});