import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogContent,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay,
    Avatar,
    Box,
    Button,
    Center,
    Flex,
    Heading,
    Image,
    Stack,
    Tag,
    Text,
    useColorModeValue,
    useDisclosure
} from '@chakra-ui/react';
import {useRef} from "react";
import {removeCustomer} from "../services/Client.js";
import {errorAlert, successAlert} from "../services/AlertToast.js";
import UpdateForm from "./forms/UpdateForm.jsx";


export default function CardWithImage({
                                          id,
                                          userName,
                                          firstName,
                                          lastName,
                                          email,
                                          gender,
                                          age,
                                          imageNumber,
                                          fetchCustomers
                                      }) {
    const randomGender = gender === "MALE" ? "men" : "women"
    const {isOpen, onOpen, onClose} = useDisclosure()
    const cancelRef = useRef()

    return (
        <Center py={6}>
            <Box
                maxW={'230px'}
                w={'full'}
                m={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'20px'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit={'cover'}
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://randomuser.me/api/portraits/${randomGender}/${imageNumber}.jpg`
                        }
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={3} align={'center'} mb={5}>
                        <Tag borderRadius={"half"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {firstName} {lastName}
                        </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}> <span>Age:</span> {age} | {gender}</Text>
                    </Stack>
                </Box>
                <Stack direction={'row'} justify={'center'} margin={'20px'} spacing={6} p={4}>
                    <Stack>
                        <UpdateForm
                            initialValues={{userName, firstName, lastName, email, age}}
                            customerId={id}
                            fetchCustomers={fetchCustomers}
                        />
                    </Stack>
                    <Stack>
                        <Button
                            bgColor={'red.400'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-8px)',
                                boxShadow: 'xl',
                                border: 'black'
                            }}
                            onClick={onOpen}
                        >
                            Remove
                        </Button>
                        <AlertDialog
                            isOpen={isOpen}
                            leastDestructiveRef={cancelRef}
                            onClose={onClose}
                        >
                            <AlertDialogOverlay>
                                <AlertDialogContent>
                                    <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                        Remove Customer
                                    </AlertDialogHeader>
                                    <AlertDialogBody>
                                        Are you sure to remove {firstName}'s profile?...
                                        You can't undo this action afterwards.
                                    </AlertDialogBody>

                                    <AlertDialogFooter>
                                        <Button ref={cancelRef} onClick={onClose}>
                                            Cancel
                                        </Button>
                                        <Button colorScheme='red' onClick={() => {
                                            removeCustomer(id).then(
                                                result => {
                                                    successAlert(
                                                        'Customer removed',
                                                        `${firstName} was successfully removed`
                                                    )
                                                    fetchCustomers();
                                                }
                                            ).catch(
                                                error => {
                                                    errorAlert(
                                                        error.code,
                                                        error.response.data.message
                                                    )
                                                }
                                            ).finally(
                                                () => {
                                                    onClose()
                                                }
                                            )
                                        }} ml={3}>
                                            Delete
                                        </Button>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialogOverlay>
                        </AlertDialog>
                    </Stack>
                </Stack>
            </Box>
        </Center>
    )
}