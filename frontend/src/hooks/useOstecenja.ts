import { useEffect, useState } from "react";
import { TipOstecenja } from "../utils/types";
import { getOstecenja } from "../utils/fetch";

export function useOstecenja() {
  const [ostecenja, setOstecenja] = useState<TipOstecenja[]>();
  useEffect(() => {
    getOstecenja().then(res => setOstecenja(res.data));
  }, []);
  return ostecenja;
}