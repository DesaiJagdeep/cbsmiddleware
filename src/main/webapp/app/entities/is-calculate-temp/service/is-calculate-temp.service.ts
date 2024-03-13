import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIsCalculateTemp, NewIsCalculateTemp } from '../is-calculate-temp.model';

export type PartialUpdateIsCalculateTemp = Partial<IIsCalculateTemp> & Pick<IIsCalculateTemp, 'id'>;

export type EntityResponseType = HttpResponse<IIsCalculateTemp>;
export type EntityArrayResponseType = HttpResponse<IIsCalculateTemp[]>;

@Injectable({ providedIn: 'root' })
export class IsCalculateTempService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/is-calculate-temps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(isCalculateTemp: NewIsCalculateTemp): Observable<EntityResponseType> {
    return this.http.post<IIsCalculateTemp>(this.resourceUrl, isCalculateTemp, { observe: 'response' });
  }

  update(isCalculateTemp: IIsCalculateTemp): Observable<EntityResponseType> {
    return this.http.put<IIsCalculateTemp>(`${this.resourceUrl}/${this.getIsCalculateTempIdentifier(isCalculateTemp)}`, isCalculateTemp, {
      observe: 'response',
    });
  }

  partialUpdate(isCalculateTemp: PartialUpdateIsCalculateTemp): Observable<EntityResponseType> {
    return this.http.patch<IIsCalculateTemp>(`${this.resourceUrl}/${this.getIsCalculateTempIdentifier(isCalculateTemp)}`, isCalculateTemp, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIsCalculateTemp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIsCalculateTemp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIsCalculateTempIdentifier(isCalculateTemp: Pick<IIsCalculateTemp, 'id'>): number {
    return isCalculateTemp.id;
  }

  compareIsCalculateTemp(o1: Pick<IIsCalculateTemp, 'id'> | null, o2: Pick<IIsCalculateTemp, 'id'> | null): boolean {
    return o1 && o2 ? this.getIsCalculateTempIdentifier(o1) === this.getIsCalculateTempIdentifier(o2) : o1 === o2;
  }

  addIsCalculateTempToCollectionIfMissing<Type extends Pick<IIsCalculateTemp, 'id'>>(
    isCalculateTempCollection: Type[],
    ...isCalculateTempsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const isCalculateTemps: Type[] = isCalculateTempsToCheck.filter(isPresent);
    if (isCalculateTemps.length > 0) {
      const isCalculateTempCollectionIdentifiers = isCalculateTempCollection.map(
        isCalculateTempItem => this.getIsCalculateTempIdentifier(isCalculateTempItem)!
      );
      const isCalculateTempsToAdd = isCalculateTemps.filter(isCalculateTempItem => {
        const isCalculateTempIdentifier = this.getIsCalculateTempIdentifier(isCalculateTempItem);
        if (isCalculateTempCollectionIdentifiers.includes(isCalculateTempIdentifier)) {
          return false;
        }
        isCalculateTempCollectionIdentifiers.push(isCalculateTempIdentifier);
        return true;
      });
      return [...isCalculateTempsToAdd, ...isCalculateTempCollection];
    }
    return isCalculateTempCollection;
  }
}
