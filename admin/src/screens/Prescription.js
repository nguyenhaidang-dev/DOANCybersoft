import Sidebar from "../components/sidebar";
import Header from "../components/Header";
import MainPrescription from "../components/Prescription/MainPrescription";

const Prescription = () =>{
    return (
        <>
        <Sidebar />
        <main className="main-wrap">
          <Header />
          <MainPrescription />
        </main>
        </>
    )
}

export default Prescription;