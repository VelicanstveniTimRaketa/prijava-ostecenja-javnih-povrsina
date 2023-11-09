import { useEffect, useState } from "react";
import { GradskiUred } from "../utils/types";
import { getGradskiUredi } from "../utils/fetch";

export function useGradskiUredi() {
  const [sviUredi, setSviUredi] = useState<GradskiUred[]>();
  useEffect(() => {
    getGradskiUredi().then(res => setSviUredi(res.data));
  }, []);
  return sviUredi;
}