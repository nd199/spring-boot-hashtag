import {Spinner, Text, Wrap, WrapItem} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {useEffect, useState} from 'react';
import {getCustomers} from "./services/Client.js";
import CardWithImage from "./components/Card.jsx";
import CForm from "./components/forms/CreationForm.jsx";
import {errorAlert} from "./services/AlertToast.js";

const App = () => {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const fetchCustomers = () => {
        setLoading(true);
        getCustomers().then(result => {
            setCustomers(result.data)
        }).catch(error => {
            setError(error.response.data.message)
            errorAlert(
                error.code,
                error.response.data.message
            )
        }).finally(() => {
            setLoading(false)
        })
    }


    useEffect(() => {
        fetchCustomers();
    }, []);


    if (loading) {
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SidebarWithHeader>
        )
    }

    if (error) {
        return (
            <SidebarWithHeader>
                <CForm
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>Sorry!.. there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if (customers.length <= 0) {
        return (
            <SidebarWithHeader>
                <CForm
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>No Customers Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <CForm
                fetchCustomers={fetchCustomers}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...customer}
                            imageNumber={index}
                            fetchCustomers={fetchCustomers}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
}

export default App;