import { useEffect, useRef, useState } from "react";

export function useToggleable(initial: boolean) {
  const [active, setActive] = useState(initial);
  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (!active) return;
    let flag = false;

    const destroy = (e: MouseEvent) => {
      if (ref.current?.contains(e.target as Node))
        return;

      if (!flag) {
        flag = true;
        return;
      }
      setActive(false);
    };
    document.addEventListener("click", destroy);
    return () => document.removeEventListener("click", destroy);
  }, [active]);

  return [active, () => setActive(!active), ref] as const;
}