import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKarkhanaVasuliRecords, NewKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';

export type PartialUpdateKarkhanaVasuliRecords = Partial<IKarkhanaVasuliRecords> & Pick<IKarkhanaVasuliRecords, 'id'>;

export type EntityResponseType = HttpResponse<IKarkhanaVasuliRecords>;
export type EntityArrayResponseType = HttpResponse<IKarkhanaVasuliRecords[]>;

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliRecordsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/karkhana-vasuli-records');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(karkhanaVasuliRecords: NewKarkhanaVasuliRecords): Observable<EntityResponseType> {
    return this.http.post<IKarkhanaVasuliRecords>(this.resourceUrl, karkhanaVasuliRecords, { observe: 'response' });
  }

  update(karkhanaVasuliRecords: IKarkhanaVasuliRecords): Observable<EntityResponseType> {
    return this.http.put<IKarkhanaVasuliRecords>(
      `${this.resourceUrl}/${this.getKarkhanaVasuliRecordsIdentifier(karkhanaVasuliRecords)}`,
      karkhanaVasuliRecords,
      { observe: 'response' },
    );
  }

  partialUpdate(karkhanaVasuliRecords: PartialUpdateKarkhanaVasuliRecords): Observable<EntityResponseType> {
    return this.http.patch<IKarkhanaVasuliRecords>(
      `${this.resourceUrl}/${this.getKarkhanaVasuliRecordsIdentifier(karkhanaVasuliRecords)}`,
      karkhanaVasuliRecords,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKarkhanaVasuliRecords>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKarkhanaVasuliRecords[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKarkhanaVasuliRecordsIdentifier(karkhanaVasuliRecords: Pick<IKarkhanaVasuliRecords, 'id'>): number {
    return karkhanaVasuliRecords.id;
  }

  compareKarkhanaVasuliRecords(o1: Pick<IKarkhanaVasuliRecords, 'id'> | null, o2: Pick<IKarkhanaVasuliRecords, 'id'> | null): boolean {
    return o1 && o2 ? this.getKarkhanaVasuliRecordsIdentifier(o1) === this.getKarkhanaVasuliRecordsIdentifier(o2) : o1 === o2;
  }

  addKarkhanaVasuliRecordsToCollectionIfMissing<Type extends Pick<IKarkhanaVasuliRecords, 'id'>>(
    karkhanaVasuliRecordsCollection: Type[],
    ...karkhanaVasuliRecordsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const karkhanaVasuliRecords: Type[] = karkhanaVasuliRecordsToCheck.filter(isPresent);
    if (karkhanaVasuliRecords.length > 0) {
      const karkhanaVasuliRecordsCollectionIdentifiers = karkhanaVasuliRecordsCollection.map(
        karkhanaVasuliRecordsItem => this.getKarkhanaVasuliRecordsIdentifier(karkhanaVasuliRecordsItem)!,
      );
      const karkhanaVasuliRecordsToAdd = karkhanaVasuliRecords.filter(karkhanaVasuliRecordsItem => {
        const karkhanaVasuliRecordsIdentifier = this.getKarkhanaVasuliRecordsIdentifier(karkhanaVasuliRecordsItem);
        if (karkhanaVasuliRecordsCollectionIdentifiers.includes(karkhanaVasuliRecordsIdentifier)) {
          return false;
        }
        karkhanaVasuliRecordsCollectionIdentifiers.push(karkhanaVasuliRecordsIdentifier);
        return true;
      });
      return [...karkhanaVasuliRecordsToAdd, ...karkhanaVasuliRecordsCollection];
    }
    return karkhanaVasuliRecordsCollection;
  }
}
