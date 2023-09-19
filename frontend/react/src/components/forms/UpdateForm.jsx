import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import UpdateCustomerForm from "../UpdateCustomerForm.jsx";


const UpdateForm = ({fetchCustomers, initialValues, customerId}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Button
            onClick={onOpen}
            colorScheme={"teal"}
            rounded={'full'}
            bgColor={"black"}
            color={'white'}
            _hover={{
                transform: 'translateY(-8px)',
                boxShadow: 'xl',
                border: 'red'
            }}
        >
            Update
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Update Customer</DrawerHeader>

                <DrawerBody>
                    <UpdateCustomerForm
                        fetchCustomers={fetchCustomers}
                        initialValues={initialValues}
                        customerId={customerId}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        onClick={(onClose)}
                        colorScheme={"teal"}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>
}
export default UpdateForm;


