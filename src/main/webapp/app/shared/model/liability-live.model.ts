export interface ILiabilityLive {
  id?: number;
  totalLiabilities?: number | null;
  supplier?: number | null;
  bankLoan?: number | null;
  other?: number | null;
  code?: string | null;
  notes?: string | null;
}

export class LiabilityLive implements ILiabilityLive {
  constructor(
    public id?: number,
    public totalLiabilities?: number | null,
    public supplier?: number | null,
    public bankLoan?: number | null,
    public other?: number | null,
    public code?: string | null,
    public notes?: string | null
  ) {}
}
