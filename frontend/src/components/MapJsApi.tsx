import { useEffect, useState, CSSProperties } from "react";
import { GoogleMap, Libraries, Marker, useJsApiLoader } from "@react-google-maps/api";
import Layout from "antd/es/layout";

const containerStyle = {
  width: "500px",
  height: "300px",
  overflow: "hidden",
};

const defaultCenter = {
  lat: 45.8131275535915,
  lng: 15.977296829223633,
};

const libraries = ["places"] as Libraries;

interface MapJsApiProps {
  style?: CSSProperties;
  center?: google.maps.LatLng | google.maps.LatLngLiteral;
  marker?: google.maps.LatLng | google.maps.LatLngLiteral;
  secondaryMarkers?: (google.maps.LatLng | google.maps.LatLngLiteral)[];
  zoom?: number;
  disabled?: boolean;
  onClick?: (point: google.maps.LatLng) => void;
}

function MapJsApi(props: MapJsApiProps) {
  const { isLoaded } = useJsApiLoader({
    id: "google-map-script",
    libraries,
    googleMapsApiKey: import.meta.env.VITE_MAPS_API_KEY
  });

  const [map, setMap] = useState<google.maps.Map | null>(null);
  const [enabled, setEnabled] = useState(!props.disabled);
  const [marker, setMarker] = useState<google.maps.LatLng | google.maps.LatLngLiteral>();
  const [secondaryMakers, setSecondaryMarkers] = useState<(google.maps.LatLng | google.maps.LatLngLiteral)[]>();

  useEffect(() => {
    setMarker(isLoaded ? props.marker : undefined);
    setSecondaryMarkers(isLoaded ? props.secondaryMarkers : undefined);
  }, [isLoaded, props.marker, props.secondaryMarkers]);

  useEffect(() => {
    if (props.disabled && enabled) {
      setMapInteractivityEnabled(false);
      setEnabled(false);
    } else if (!props.disabled && !enabled) {
      setMapInteractivityEnabled(true);
      setEnabled(true);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.disabled, isLoaded]);

  useEffect(() => {
    map && setMapInteractivityEnabled(enabled);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map]);

  function handleMapClick(event: google.maps.MapMouseEvent) {
    if (!enabled || !event.latLng) return;
    props.onClick && props.onClick(event.latLng);
  }

  function setMapInteractivityEnabled(e: boolean) {
    map?.setOptions({ draggable: e, zoomControl: e, scrollwheel: e, disableDoubleClickZoom: !e });
  }

  return (
    <Layout className="shadow" style={{ ...props.style, ...containerStyle, borderRadius: "1em" }}>
      {isLoaded && (
        <GoogleMap
          mapContainerStyle={containerStyle}
          center={props.center || defaultCenter}
          zoom={props.zoom || 11}
          onLoad={map => setMap(map)}
          onUnmount={() => setMap(null)}
          options={{ fullscreenControl: false, streetViewControl: false }}
          onClick={handleMapClick}
        >
          {isLoaded && marker && <Marker position={marker}></Marker>}
          {secondaryMakers && secondaryMakers.map((marker, i) => <Marker key={i} position={marker} />)}
        </GoogleMap>
      )}
    </Layout>
  );
}

export default MapJsApi;
