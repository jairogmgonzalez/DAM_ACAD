import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import { Home } from "./pages/Home";
import { About } from "./pages/About";
import { Contact } from "./pages/Contact";
import { Register } from "./pages/Register";
import { Summary } from "./pages/Summary";
import { FormProvider, FormContext } from "./context/FormContext";
import NavigationBar from "./components/NavigationBar";
import { useContext } from "react";

function App() {
  return (
    <FormProvider>
      <Router>
        <AppContent />
      </Router>
    </FormProvider>
  );
}

// Componente dentro de FormProvider
function AppContent() {
  const { user } = useContext(FormContext);

  return (
    <>
      {user && <NavigationBar />}
      <Routes>
        {!user ? (
          <Route path="*" element={<Navigate to="/register" />} />
        ) : (
          <>
            <Route path="/home" element={<Home />} />
            <Route path="/about" element={<About />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/summary" element={<Summary />} />
            <Route path="*" element={<Navigate to="/home" />} />
          </>
        )}
        <Route path="/register" element={<Register />} />
      </Routes>
    </>
  );
}

export default App;