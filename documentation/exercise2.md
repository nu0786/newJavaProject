# Exercise 2
While our current implementation of `performTransferWithoutApproval` is thread safe, it does not scale because it contains a mutex (`synchronized`) in `TransferServiceImpl` on method `performTransferWithoutApproval`.
Remove the mutex and run the test `TransferServiceIT``performTransferWithoutApproval_shouldReturnTrue_whenBusinessRequirementAreFulfill_andExecuteInParallel`

As you can see, the test is failing.

1. Put in place the code that would make the test passing, while enabling parallel transfer. Existing code may need a complete rewrite, but **Do not change the test**
