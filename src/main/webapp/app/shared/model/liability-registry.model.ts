export interface ILiabilityRegistry {
  id?: number;
  today?: Date;
  totalLiabilities?: number | null;
  supplier?: number | null;
  bankLoan?: number | null;
  other?: number | null;
  code?: string | null;
  notes?: string | null;
}

export class LiabilityRegistry implements ILiabilityRegistry {
  constructor(
    public id?: number,
    public today?: Date,
    public totalLiabilities?: number | null,
    public supplier?: number | null,
    public bankLoan?: number | null,
    public other?: number | null,
    public code?: string | null,
    public notes?: string | null
  ) {}
}
