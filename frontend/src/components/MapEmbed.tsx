function MapEmbed() {
  return (
    <iframe
      width="600"
      height="450"
      style={{ border: 0 }}
      loading="lazy"
      allowFullScreen
      referrerPolicy="no-referrer-when-downgrade"
      src={`https://www.google.com/maps/embed/v1/place?region=HR&language=hr&key=${import.meta.env.VITE_MAPS_API_KEY}&q=FER,Zagreb`}
    >
    </iframe>
  );
}

export default MapEmbed;
