// An object implementing Waveform should be a function mapping time (in the
// interval [0,1]) to amplitude (in the interval [-1,1]).
interface Waveform {
    public double at(double t);
}