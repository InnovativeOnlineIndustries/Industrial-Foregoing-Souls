package com.buuz135.industrialforegoingsouls.capabilities;


public interface ISoulHandler {

    enum Action {
        EXECUTE, SIMULATE;

        public boolean execute() {
            return this == EXECUTE;
        }

        public boolean simulate() {
            return this == SIMULATE;
        }
    }

    /**
     * Returns the number of soul storage units ("tanks") available
     *
     * @return The number of soul tanks available
     */
    int getSoulTanks();

    /**
     * Returns the amount in a given tank.

     * @param tank Tank to query.
     * @return Amount in a given tank. 0 if the tank is empty.
     */
    int getSoulInTank(int tank);

    /**
     * Retrieves the maximum fluid amount for a given tank.
     *
     * @param tank Tank to query.
     * @return The maximum fluid amount held by the tank.
     */
    int getTankCapacity(int tank);

    /**
     * Fills soul into internal tanks, distribution is left entirely to the ISoulHandler.
     *
     * @param amount The amount you want to be filled
     * @param action   If SIMULATE, fill will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) filled.
     */
    int fill(int amount, Action action);

    /**
     * Drains souls out of internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param maxDrain Maximum amount of souls to drain.
     * @param action   If SIMULATE, drain will only be simulated.
     * @return The number representing the souls that was (or would have been, if
     *         simulated) drained.
     */
    int drain(int maxDrain, Action action);
}
